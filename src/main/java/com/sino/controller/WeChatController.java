package com.sino.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sino.utils.RecogUtil;
import com.sino.utils.SaveImgUtil;
import com.sino.config.WechatAccountConfig;

@Controller
@RequestMapping("/wechat")
public class WeChatController {
	
	@Autowired
	WxMpService wxMpService;
	
    @Autowired
    WechatAccountConfig accountConfig;
	
	@RequestMapping("/authorize")
	public String authorize(){
		
		String url = accountConfig.getMpUrl()+"/wechat/userInfo";
		String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, null);
		
		return "redirect:"+redirectUrl;
	}
	
	@RequestMapping("/userInfo")
	public String userInfo(@RequestParam("code")String code,
			@RequestParam("state")String returnUrl){
		System.out.println("/userInfo  code = "+code);
		System.out.println("/userInfo  returnUrl = "+returnUrl);
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            System.out.println(e);
        }
        System.out.println("/userInfo  token = "+wxMpOAuth2AccessToken.getAccessToken());

        String openId = wxMpOAuth2AccessToken.getOpenId();
        String url = ""; 
        return "redirect:"+url+"?openid=" + openId;
	}
	
	
	@RequestMapping("/savePicture")
	@ResponseBody
	public String savePicture(String mediaId){
		System.out.println("/savePicture   mediaId = "+mediaId);
		
        String token = "";
		try {
			token = wxMpService.getAccessToken();
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
        
        System.out.println("token="+token);
        String filename = SaveImgUtil.downloadMedia(token,mediaId);
        System.out.println("filename="+filename);
        String xml = RecogUtil.recog(filename);
        
        File f = new File(filename);
   	 	f.delete();
        
		return xml;
	}

	/**
	 * 调用识别
	 * @param model
	 * @return
	 */
	@RequestMapping("/recog")
	public String recog(Model model){
		String url = accountConfig.getMpUrl()+"/wechat/recog";
		try {
			WxJsapiSignature wxJsapi = wxMpService.createJsapiSignature(url);
			System.out.println("appid = "+wxJsapi.getAppId());
			System.out.println("NonceStr = "+wxJsapi.getNonceStr());
			System.out.println("Signature = "+wxJsapi.getSignature());
			System.out.println("Timestamp = "+wxJsapi.getTimestamp());
			System.out.println("Url = "+wxJsapi.getUrl());
			model.addAttribute("wxJsapi", wxJsapi);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return "recog";
	}
	
	@RequestMapping("/createMenu")
	public void createMenu(){
		
        //创建菜单
        //创建一级菜单
        WxMenuButton button1=new WxMenuButton();
        button1.setType("view"); //点击事件按钮
        button1.setName("点我啊");
        button1.setUrl(accountConfig.getMpUrl()+"/wechat/recog"); //根据标志获取点击菜单
         
        WxMenuButton button2=new WxMenuButton();
        button2.setName("关于");
        button2.setType("view");
        button2.setUrl("http://www.netocr.com");  //必须添加http
         
         
         
        List<WxMenuButton> buttons=new ArrayList<WxMenuButton>();
        buttons.add(button1);
        buttons.add(button2);
         
        WxMenu menu=new WxMenu();
        menu.setButtons(buttons);
         
        try {
        	wxMpService.getMenuService().menuCreate(menu);
        	System.out.println("菜单创建成功");
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
	}
}
