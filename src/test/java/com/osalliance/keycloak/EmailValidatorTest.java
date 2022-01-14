package com.osalliance.keycloak;

import static org.mockito.Mockito.*;

import org.jboss.resteasy.spi.HttpRequest;
import org.junit.Test;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.events.Details;
import org.keycloak.events.EventBuilder;
import org.keycloak.models.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.ws.rs.core.MultivaluedMap;
import java.util.*;

public class EmailValidatorTest {

    @Test
    public void testInvalidEmail(){

        HttpRequest httpRequest = Mockito.mock(HttpRequest.class);
        ValidationContext context = Mockito.mock(ValidationContext.class);
        AuthenticatorConfigModel authenticatorConfigModel = Mockito.mock(AuthenticatorConfigModel.class);


        MultivaluedMap<String,String> formData = Mockito.mock(MultivaluedMap.class);

        EventBuilder eventBuilder= mock(EventBuilder.class);

        Map<String,String> config = new HashMap<>();
        config.put(EmailValidatorFactory.PROVIDER_REGEX,".*@test.tld");

        when(httpRequest.getDecodedFormParameters()).thenReturn(formData);
        when(formData.getFirst(UserModel.EMAIL)).thenReturn("max.mustermann@test.tld");
        when(eventBuilder.detail(Details.REGISTER_METHOD, "form")).thenReturn(eventBuilder);
        when(context.getEvent()).thenReturn(eventBuilder);
        when(context.getHttpRequest()).thenReturn(httpRequest);
        when(context.getAuthenticatorConfig()).thenReturn(authenticatorConfigModel);
        when(authenticatorConfigModel.getConfig()).thenReturn(config);


        EmailValidator emailValidator = new EmailValidator();
        emailValidator.validate(context);

        verify(context,times(1)).error("email not allowed");
    }

    @Test
    public void testValidEmail(){

        HttpRequest httpRequest = Mockito.mock(HttpRequest.class);
        ValidationContext context = Mockito.mock(ValidationContext.class);
        AuthenticatorConfigModel authenticatorConfigModel = Mockito.mock(AuthenticatorConfigModel.class);

        MultivaluedMap<String,String> formData = Mockito.mock(MultivaluedMap.class);

        EventBuilder eventBuilder= mock(EventBuilder.class);

        Map<String,String> config = new HashMap<>();
        config.put(EmailValidatorFactory.PROVIDER_REGEX,".*@test.tld");

        when(httpRequest.getDecodedFormParameters()).thenReturn(formData);
        when(formData.getFirst(UserModel.EMAIL)).thenReturn("max.mustermann@example.tld");
        when(eventBuilder.detail(Details.REGISTER_METHOD, "form")).thenReturn(eventBuilder);
        when(context.getEvent()).thenReturn(eventBuilder);
        when(context.getHttpRequest()).thenReturn(httpRequest);
        when(context.getAuthenticatorConfig()).thenReturn(authenticatorConfigModel);
        when(authenticatorConfigModel.getConfig()).thenReturn(config);


        EmailValidator emailValidator = new EmailValidator();
        emailValidator.validate(context);

        verify(context,times(1)).success();
    }

}
