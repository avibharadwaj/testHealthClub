package com.cmpe202.HealthClubManagement.Authentication;

import java.io.IOException;

// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cmpe202.HealthClubManagement.MemberDetails.CustomMemberDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  // @Primary   
  // @Bean
  // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  //   http.cors().and().csrf().disable()
  //         .authorizeHttpRequests()
  //         .requestMatchers("/memberInfo").authenticated()
  //         .requestMatchers("/registerMember").permitAll()
  //         .requestMatchers("/home").permitAll()
  //         .and()
  //         .formLogin()
  //         .usernameParameter("username")
  //         .defaultSuccessUrl("/memberInfo", true)
  //         .permitAll()
  //         .loginPage("/login")
  //         .loginProcessingUrl("/process-login")
  //         .failureUrl("/login?error=true")
  //         .permitAll()
  //         .and()
  //         .csrf().disable()
  //         .logout()
  //         .logoutSuccessUrl("/").permitAll();
  //   return http.build();
  // }
  @Bean
  public UserDetailsService userDetailsService() {
      return new CustomMemberDetailsService();
  }
   
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }	

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService());
      authProvider.setPasswordEncoder(passwordEncoder());
       
      return authProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(authenticationProvider());
  }
  public void configure(WebSecurity web) throws Exception {
      web
          .ignoring()
          .antMatchers("/h2-ui/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
          .antMatchers("/memberInfo").authenticated()
          .antMatchers("/registerMember").permitAll()
          .antMatchers("/home").permitAll()
          .and()
          .formLogin()
              .usernameParameter("username")
              .defaultSuccessUrl("/memberInfo")
              .permitAll()
              .loginPage("/login")
              .loginProcessingUrl("/process-login")
              .failureUrl("/login?error=true")
              .permitAll()
          .and()
          .csrf().disable()
          .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
          .logoutSuccessUrl("/home").deleteCookies("JSESSIONID")
          .invalidateHttpSession(true); 
          
  }

}


