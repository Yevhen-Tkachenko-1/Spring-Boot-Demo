package yevhent.demo.springboot.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapPasswordComparisonAuthenticationManagerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.server.UnboundIdContainer;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(customizer -> customizer.requestMatchers("/", "/home").permitAll());
        http.authorizeHttpRequests(customizer -> customizer.requestMatchers("/customers/**").hasRole("USER"));
        http.authorizeHttpRequests(customizer -> customizer.requestMatchers("/orders").hasRole("ADMIN"));
        http.authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated());

        http.formLogin(customizer -> customizer
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll());

        http.logout(customizer -> customizer
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login?logout").permitAll());

        return http.build();
    }

    @Bean
    UnboundIdContainer ldapContainer() {
        return new UnboundIdContainer("dc=mymarketplace,dc=org", "classpath:users.ldif");
    }

    ContextSource contextSource(UnboundIdContainer container) {
        return new DefaultSpringSecurityContextSource("ldap://localhost:8399/dc=mymarketplace,dc=org");
    }

    @Bean
    LdapAuthoritiesPopulator authorities(BaseLdapPathContextSource contextSource) {
        DefaultLdapAuthoritiesPopulator authorities = new DefaultLdapAuthoritiesPopulator(contextSource, "ou=groups");
        authorities.setGroupSearchFilter("(member={0})");
        return authorities;
    }

    @Bean
    public AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource, LdapAuthoritiesPopulator authorities) {
        LdapPasswordComparisonAuthenticationManagerFactory factory =
                new LdapPasswordComparisonAuthenticationManagerFactory(contextSource, new BCryptPasswordEncoder());
        factory.setUserSearchBase("ou=people");
        factory.setUserSearchFilter("(uid={0})");
        factory.setPasswordAttribute("userPassword");
        factory.setLdapAuthoritiesPopulator(authorities);
        return factory.createAuthenticationManager();
    }

}