package io.github.tonvanbart.ldapexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${ldap.url}")
    private String contextUrl;

    @Value("${ldap.user.dn.pattern}")
    private String userDnPattern;

    @Value("${ldap.groups.search.base}")
    private String groupsSearchBase;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/actuator/health").anonymous()
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin();
    }

    /**
     * Set up LDAP authentication, based on the structure of the LDAP tree.
     * Username from login is plugged into <code>{0}</code>
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        log.info("Connecting to LDAP at '{}'", contextUrl);
        authenticationManagerBuilder
                .ldapAuthentication()
                .userDnPatterns(userDnPattern)
                .groupSearchBase(groupsSearchBase)
                .groupSearchFilter("(member={0})")
                .contextSource()
                .url(contextUrl);
    }

}
