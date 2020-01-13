package dao.User;

        import org.apache.ibatis.annotations.Param;
        import pojo.User;

public interface UserMapper {
    /**
     * 根据用户名密码登录
     * @param username
     * @param password
     * @return
     */
    User login(@Param("username") String username, @Param("password") String password);

    /**
     * 使用手机号登录
     * @param phone
     * @return
     */
    User loginByPhone(@Param("phone")String phone);
}
