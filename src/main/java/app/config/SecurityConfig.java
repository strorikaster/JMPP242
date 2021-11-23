package app.config;

import app.config.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug = true)
@ComponentScan("app")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя
    private final LoginSuccessHandler loginSuccessHandler;

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //auth.inMemoryAuthentication().withUser("ADMIN").password("ADMIN").roles("ADMIN");
//        auth.userDetailsService();
//    }
    @Autowired
    public SecurityConfig(@Qualifier("userServiceImpl") UserDetailsService userDetailsService, LoginSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
}

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // конфигурация для прохождения аутентификации
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
             .csrf()
                .disable()
        .authorizeRequests()
                //.antMatchers("users/new").not().fullyAuthenticated()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/vip/**").hasAnyAuthority("VIP", "ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN", "VIP")
               // .antMatchers("/user/**").hasAnyAuthority("VIP", "USER")
                .anyRequest().authenticated();
                //.and()
                //.formLogin()
                //.permitAll()
                //.and()
//                .logout()
//                .permitAll()
//                .logoutSuccessUrl("/");

        httpSecurity.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //.logout()
                .logoutUrl("/logout")
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/login");
                //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
                //.and().csrf().disable();




//        http.formLogin().successHandler(loginSuccessHandler).loginProcessingUrl("/login").permitAll();
//
//        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();
//
//        http.authorizeRequests().antMatchers("/login").anonymous().
//                antMatchers("/admin").access("hasAnyRole('ROLE_ADMIN')").
//                antMatchers("/admin/**").access("hasAnyRole('ROLE_ADMIN')").
//                antMatchers("/user").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')").
//                antMatchers("/user/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

        httpSecurity.formLogin()
                // указываем страницу с формой логина
                //.loginPage("/users/login")
                //указываем логику обработки при логине
                .successHandler(loginSuccessHandler)
                // указываем action с формы логина
                //.loginProcessingUrl("/users/login")
                // Указываем параметры логина и пароля с формы логина
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                // даем доступ к форме логина всем
                .permitAll();
//
//        http.logout()
//                // разрешаем делать логаут всем
//                .permitAll()
//                // указываем URL логаута
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                // указываем URL при удачном логауте
//                .logoutSuccessUrl("/login?logout")
//                //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
//                .and().csrf().disable();
//
//        http
//                // делаем страницу регистрации недоступной для авторизированных пользователей
//                .authorizeRequests()
//                //страницы аутентификаци доступна всем
//                .antMatchers("/users/login").anonymous()
//                // защищенные URL
//                .antMatchers("/users/").access("hasAnyRole('ADMIN')").anyRequest().authenticated();
//        httpSecurity.authorizeRequests()
//                .antMatchers("/users/login").permitAll() // доступность всем
//                .antMatchers("/users/").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')") // разрешаем входить на /user пользователям с ролью User
//                .and()
//                .formLogin()
//                //.loginPage("/users/login")  // Spring сам подставит свою логин форму
//                .successHandler(loginSuccessHandler); // подключаем наш SuccessHandler для перенеправления по ролям
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
