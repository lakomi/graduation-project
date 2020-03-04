package com.edu.graduation.utils;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName MD5Util
 * @Description  使用md5 加密密码
 * @Author q
 * @Date 18-9-1 上午10:45
 */
public class MD5Util {

    private static final String POOL = "AaBbCcDdEeFfGgHhIiJjKLMmNnOPQqRrSTUVWXYyZ0123456789";

    private static final String SALT = "signIn179";

    /**
     * md5加密
     * @param str
     * @return
     */
    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }

    public static String inputToFormPass(String password){
        String dealPass = ""+SALT.charAt(0)+SALT.charAt(2)+SALT.charAt(5)+password+SALT.charAt(7)+SALT.charAt(8);
        return md5(dealPass);
    }

    /**
     * 将表单提交的密码经过处理并加密得到数据库中存储的密码
     * @param formPass
     * @param saltDB
     * @return
     */
    public static String formPassToDBPass(String formPass,String saltDB){
        String dealPass = ""+saltDB.charAt(1)+saltDB.charAt(3)+formPass+saltDB.charAt(7)+saltDB.charAt(5);
        return md5(dealPass);
    }

    /**
     * 直接将明文密码转成数据库密码
     * @param input
     * @param saltDB
     * @return
     */
    public static String inputToDBPass(String input,String saltDB){
        String formPass = inputToFormPass(input);
        String dbPass = formPassToDBPass(formPass,saltDB);
        return dbPass;
    }
    /**
     * 获取随机的salt  至少8位
     * @return
     */
    public static String getRandomSalt(){
        return KeyUtil.getRandomString(8);
    }



    /**
     * 验证提交的密码是否与数据库密码匹配
     * @param formPass
     * @param dbPass
     * @param salt
     * @return
     */
    public static Boolean passIsCorrect(String formPass,String dbPass,String salt){
        String calcPass = MD5Util.formPassToDBPass(formPass,salt);
        if (calcPass.equals(dbPass)){
            return true;
        }else {
            return false;
        }
    }


    public static void main(String[] args) {
        System.out.println(inputToFormPass("111111"));
    }


}
