package se.andolf.configuration;

/**
 * Created by Thomas on 2016-09-24.
 */
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .logout()
//                .logoutSuccessUrl("/swagger-ui.html")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/category").hasRole("USER")
//                .and()
//                .formLogin()
//                .and()
//                .httpBasic();
//    }
//}
