package com.nationalchip.iot.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/6/18 3:08 PM
 * @Modified:
 */

/**
 * 终端鉴权管理器
 *
 * 当终端设备调用DDI API时，会附带其id或者controllerId授予的GatewayToken或者
 * SecurityToken，该授权管理器会通过查询数据库来验证token是否一致、Ip是否一致来
 * 确定是否给予授权。
 *
 */


public class PreTokenAuthenticationProvider extends PreAuthenticatedAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreAuthenticatedAuthenticationToken.class);
    private final List<String> authorizedSourceIps;

    /**
     * Creates a new PreAuthTokenSourceTrustAuthenticationProvider without
     * source IPs, which disables the source IP check.
     */
    public PreTokenAuthenticationProvider() {
        authorizedSourceIps = null;
    }

    /**
     * Creates a new PreAuthTokenSourceTrustAuthenticationProvider with given
     * source IP addresses which are trusted and should be checked against the
     * request remote IP address.
     *
     * @param authorizedSourceIps
     *            a list of IP addresses.
     */
    public PreTokenAuthenticationProvider(final List<String> authorizedSourceIps) {
        this.authorizedSourceIps = authorizedSourceIps;
    }

    /**
     * Creates a new PreAuthTokenSourceTrustAuthenticationProvider with given
     * source IP addresses which are trusted and should be checked against the
     * request remote IP address.
     *
     * @param authorizedSourceIps
     *            a list of IP addresses.
     */
    public PreTokenAuthenticationProvider(final String... authorizedSourceIps) {
        this.authorizedSourceIps = new ArrayList<>();
        for (final String ip : authorizedSourceIps) {
            this.authorizedSourceIps.add(ip);
        }
    }

    @Override
    public Authentication authenticate(final Authentication authentication) {
        if (!supports(authentication.getClass())) {
            return null;
        }

        final PreAuthenticatedAuthenticationToken token = (PreAuthenticatedAuthenticationToken) authentication;
        final Object credentials = token.getCredentials();
        final Object principal = token.getPrincipal();
        final Object tokenDetails = token.getDetails();

        if (principal == null) {
            throw new BadCredentialsException("The provided principal and credentials are not match");
        }

        boolean successAuthentication = calculateAuthenticationSuccess(principal, credentials, tokenDetails);


//        //鉴权完成后授权，默认具有controller和controller_download角色的权限
//        if (successAuthentication) {
//            final Collection<GrantedAuthority> controllerAuthorities = new ArrayList<>();
//            controllerAuthorities.add(new SimpleGrantedAuthority(SpringEvalExpressions.CONTROLLER_ROLE));
//            controllerAuthorities.add(new SimpleGrantedAuthority(SpringEvalExpressions.CONTROLLER_DOWNLOAD_ROLE));
//            final PreAuthenticatedAuthenticationToken successToken = new PreAuthenticatedAuthenticationToken(principal,
//                    credentials, controllerAuthorities);
//            successToken.setDetails(tokenDetails);
//            return successToken;
//        }

        throw new BadCredentialsException("The provided principal and credentials are not match");
    }

    /**
     *
     * The credentials may either be of type HeaderAuthentication or of type
     * Collection<HeaderAuthentication> depending on the authentication mode in
     * use (the latter is used in case of trusted reverse-proxy). It is checked
     * whether principal equals credentials (respectively if credentials
     * contains principal in case of collection) because we want to check if
     * e.g. controllerId containing in the URL equals the controllerId in the
     * special header set by the reverse-proxy which extracted the CN from the
     * certificate.
     *
     * @param tokenDetails
     *            authentication details
     * @return <code>true</code> if authentication succeeded, otherwise
     *         <code>false</code>
     */
    private boolean calculateAuthenticationSuccess(Object principal, Object credentials, Object tokenDetails) {
        boolean successAuthentication = false;
        if (credentials instanceof Collection) {
            final Collection<?> multiValueCredentials = (Collection<?>) credentials;
            if (multiValueCredentials.contains(principal)) {
                successAuthentication = checkSourceIPAddressIfNeccessary(tokenDetails);
            }
        } else if (principal.equals(credentials)) {
            successAuthentication = checkSourceIPAddressIfNeccessary(tokenDetails);
        }

        return successAuthentication;
    }

    private boolean checkSourceIPAddressIfNeccessary(final Object tokenDetails) {
        boolean success = authorizedSourceIps == null;
        String remoteAddress = null;
        // controllerIds in URL path and request header are the same but is the
        // request coming
        // from a trustful source, like the reverse proxy.
//        if (authorizedSourceIps != null) {
//            if (!(tokenDetails instanceof TenantAwareWebAuthenticationDetails)) {
//                // is not of type WebAuthenticationDetails, then we cannot
//                // determine the remote address!
//                LOGGER.error(
//                        "Cannot determine the controller remote-ip-address based on the given authentication token - {} , token details are not TenantAwareWebAuthenticationDetails! ",
//                        tokenDetails);
//                success = false;
//            } else {
//                remoteAddress = ((TenantAwareWebAuthenticationDetails) tokenDetails).getRemoteAddress();
//                if (authorizedSourceIps.contains(remoteAddress)) {
//                    // source ip matches the given pattern -> authenticated
//                    success = true;
//                }
//            }
//        }

        if (!success) {
            throw new InsufficientAuthenticationException("The remote source IP address " + remoteAddress
                    + " is not in the list of trusted IP addresses " + authorizedSourceIps);
        }

        // no trusted IP check, because no authorizedSourceIPs configuration
        return true;
    }

//    @Override
//    public boolean supports(final Class<?> authentication) {
//        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
//    }

}
