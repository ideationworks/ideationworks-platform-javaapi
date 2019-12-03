package ideationworks.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springblack.security.JWTAuthenticationFilter;

@Configuration("ApplicationSecurityConfig")
@EnableWebSecurity
@Order(1)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().authorizeRequests().antMatchers("/monitoring/is_alive",
                                                                           "/users/login",
                                                                           "/sso/**",
                                                                           "/socket/**",
                                                                           "/users/reset/**",
                                                                           "/users/register",
                                                                           "/users/confirm/**",
                                                                           "/categories/search",
                                                                           "/swagger-ui.html/**", "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**").permitAll().anyRequest().authenticated().and()
            // And filter other requests to check the presence of JWT in header
            .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}
