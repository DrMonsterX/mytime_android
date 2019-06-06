package com.example.dy.mytime.UserPackage;


public class LoginController extends UserController implements ILogin {
    public static User user;
//    private MyDatabaseController controller;
    public LoginController(){
        super();
    }
    //检查用户登录信息
    public int checkLogin(int user_id,String password) {

            //执行登录验证线程
            Thread thread=new LoginThread(user_id);
            thread.start();
        try
        {
            thread.join();//等待登录验证线程执行结束后，主线程继续执行
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }




        if(user==null) {
            //用户ID无效
            return 2;
        }
        if(user.getPassword().equals(Integer.toString(password.hashCode())))
        {
            //登录成功
            return 0;
        }
        //密码错误
        return 1;
    }



}
