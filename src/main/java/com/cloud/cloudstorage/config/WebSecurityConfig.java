package com.cloud.cloudstorage.config;

import com.cloud.cloudstorage.security.JwtAuthenticationEntryPoint;
import com.cloud.cloudstorage.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService jwtUserDetailsService;
    private final JwtAuthenticationFilter JwtAuthenticationFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //Конфигурация безопасности
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()//Отключаем CSRF
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests().antMatchers("/login")
                .permitAll() //Разрешаем доступ к /login всем пользователям.
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/logout").deleteCookies("JSESSIONID").clearAuthentication(true)
                .logoutSuccessUrl("/login")//Выход из сервиса
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)//Настройка handler'a
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //Управление сеансами

        //Custom filter
        httpSecurity.addFilterBefore(JwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
