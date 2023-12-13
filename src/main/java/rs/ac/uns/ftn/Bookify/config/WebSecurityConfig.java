package rs.ac.uns.ftn.Bookify.config;

import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rs.ac.uns.ftn.Bookify.config.filters.JWTAuthenticationFilter;
import rs.ac.uns.ftn.Bookify.config.utils.JWTUtils;
import rs.ac.uns.ftn.Bookify.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private JWTUtils jwtUtils;

    @Bean
    public WebMvcConfigurer CORSConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "PUT", "POST", "DELETE");
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityContext((securityContext) -> securityContext.securityContextRepository(new RequestAttributeSecurityContextRepository()));
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(httpSecurityCorsConfigurer -> CORSConfigurer());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(restAuthenticationEntryPoint));
        http.authorizeHttpRequests(request ->{
            request.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/accommodations/image/{imageId}","/api/v1/accommodations/search", "/api/v1/accommodations/details/{accommodationId}",
                            "/api/v1/accommodations/top-accommodations", "/api/v1/accommodations/top-locations", "/api/v1/users/image/{imageId}", "/api/v1/users/{userId}/forgot-password",
                            "/api/v1/accommodations/images/{accommodationId}", "/api/v1/reviews/accommodation/{accommodationId}", "/api/v1/reviews/owner/{ownerId}").permitAll()
                    .requestMatchers(HttpMethod.POST,"/api/v1/users/login", "/api/v1/users","/api/v1/users/activate-account/{userId}",
                            "/api/v1/accommodations/filter").permitAll()
                    .anyRequest().authenticated()
            ;
        });

        http.addFilterBefore(new JWTAuthenticationFilter(jwtUtils, userDetailsService()), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
//                .requestMatchers(HttpMethod.POST,"/api/v1/users/login")
                .requestMatchers(HttpMethod.GET, "/", "/webjars/*", "/*.html", "favicon.ico",
                        "/*/*.html", "/*/*.css", "/*/*.js");

    }
}