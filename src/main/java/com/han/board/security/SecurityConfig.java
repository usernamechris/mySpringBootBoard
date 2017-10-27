package com.han.board.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 적용
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    MyUsersService myUsersService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("security config....");

        http.authorizeRequests().antMatchers("/guest/**").permitAll();
        http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

        http.formLogin().loginPage("/login");

        http.exceptionHandling().accessDeniedPage("/accessDenied");

        // 세션 무효화
        http.logout().logoutUrl("/logout").invalidateHttpSession(true);

//        http.userDetailsService(myUsersService);

        http.rememberMe()
                .key("myBoard") //key는 쿠키생성에 사용되는 암호키
                .userDetailsService(myUsersService)
                .tokenRepository(getJDBCRepository())
                .tokenValiditySeconds(60 * 60 * 24);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth ) throws Exception {
        log.info("build Auth global....");

        /* userService를 사용하면 의미없어짐 */
//        String query1 =
//                "select uid username,  upw password, true enabled from tbl_members where uid=?";
//
//        String query2 =
//                "select member uid, role_name role from tbl_member_roles where member=?";
//
////        auth.inMemoryAuthentication()
////                .withUser("manager")
////                .password("1111")
////                .roles("MANAGER");
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(query1)
//                .rolePrefix("ROLE_")
//                .authoritiesByUsernameQuery(query2);

        // password encoder는 AuthenticationManagerBuilder에서 처리
        auth.userDetailsService(myUsersService).passwordEncoder(passwordEncoder());
    }

    private PersistentTokenRepository getJDBCRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
