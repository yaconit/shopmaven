package dao.User;

        import org.apache.ibatis.annotations.Param;
        import pojo.User;

public interface UserMapper {
    User login(@Param("username") String username, @Param("password") String password);
}
