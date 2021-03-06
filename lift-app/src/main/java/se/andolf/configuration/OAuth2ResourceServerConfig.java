package se.andolf.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas on 2017-11-05.
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(new MyAccessTokenConverter());
        jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());

    }

    public class MyAccessTokenConverter implements UserAuthenticationConverter {

        @Override
        public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
            return null;
        }

        @Override
        public Authentication extractAuthentication(Map<String, ?> map) {
            final String username = (String) map.get("sub");

            final UserDetails jwtUser = new JwtUser(username, "N/A", true, Collections.singletonList(new SimpleGrantedAuthority("ADMIN_ROLE")));
            return new UsernamePasswordAuthenticationToken(jwtUser, "N/A", Collections.singletonList(new SimpleGrantedAuthority("ADMIN_ROLE")));
        }
    }

    public class JwtUser implements UserDetails {

        private final String username;
        private final String password;
        private final boolean active;
        private final List<GrantedAuthority> authorities;

        public JwtUser(String username, String password, boolean active, List<GrantedAuthority> authorities) {
            this.username = username;
            this.password = password;
            this.active = active;
            this.authorities = authorities;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return active;
        }

        @Override
        public boolean isAccountNonLocked() {
            return active;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return active;
        }

        @Override
        public boolean isEnabled() {
            return active;
        }
    }
}
