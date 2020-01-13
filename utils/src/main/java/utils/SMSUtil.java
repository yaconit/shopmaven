package utils;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;
import java.util.Set;

/**
 * 短信工具类
 */
public final class SMSUtil {

    // 服务器地址
    private String serverIp;
    // 服务器端口
    private String serverPort;
    // ACCOUNT SID
    private String accountSId;
    // AUTH TOKEN
    private String accountToken;
    // AppID
    private String appId;
    // template Id
    private String templateId;

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public void setAccountSId(String accountSId) {
        this.accountSId = accountSId;
    }

    public void setAccountToken(String accountToken) {
        this.accountToken = accountToken;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * 发送短息
     *
     * @param phone    接收的手机号码
     * @param valicode 验证码
     * @param minute   有效分钟数
     * @return 发送成功返回true，发送失败返回false
     */
    public boolean send(String phone, String valicode, int minute) {
        String serverIp = this.serverIp;
        String serverPort = this.serverPort;
        String accountSId = this.accountSId;
        String accountToken = this.accountToken;
        String appId = this.appId;
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        String to = phone;
        String templateId = this.templateId;
        String[] datas = {valicode, minute+"分钟"};
        HashMap<String, Object> result = sdk.sendTemplateSMS(to, templateId, datas);
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
            return true;
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));

            return false;
        }
    }
}
