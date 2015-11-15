package de.saphijaga.spoozer.config.resolver;

import de.saphijaga.spoozer.web.authentication.Session;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static org.springframework.messaging.simp.SimpMessageHeaderAccessor.getSessionAttributes;

/**
 * Created by samuel on 17.10.15.
 */
public class UserDetailsArgumentResolver implements HandlerMethodArgumentResolver, org.springframework.web.method.support.HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserDetails.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (webRequest.getNativeRequest() instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) webRequest
                    .getNativeRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                return session.getAttribute(Session.USER);
            }
        }
        return null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception {
        Map<String, Object> session = getSessionAttributes(message.getHeaders());
        return session.get(Session.USER);
    }
}