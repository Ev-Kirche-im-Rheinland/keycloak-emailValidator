package com.osalliance.keycloak;

import static org.mockito.Mockito.*;

import org.jboss.resteasy.spi.HttpRequest;
import org.junit.Test;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.events.Details;
import org.keycloak.events.EventBuilder;
import org.keycloak.models.*;
import org.keycloak.services.resources.AttributeFormDataProcessor;
import org.keycloak.userprofile.UserProfileAttributes;
import org.keycloak.userprofile.profile.representations.AttributeUserProfile;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.ws.rs.core.MultivaluedMap;
import java.util.*;

public class EmailValidatorTest {

    @Test
    public void testInvalidEmail(){

        AttributeUserProfile userProfile = Mockito.mock(AttributeUserProfile.class);
        UserProfileAttributes userProfileAttributes = Mockito.mock(UserProfileAttributes.class);
        HttpRequest httpRequest = Mockito.mock(HttpRequest.class);
        ValidationContext context = Mockito.mock(ValidationContext.class);
        AuthenticatorConfigModel authenticatorConfigModel = Mockito.mock(AuthenticatorConfigModel.class);

        MockedStatic<AttributeFormDataProcessor> mocked = mockStatic(AttributeFormDataProcessor.class);

        MultivaluedMap<String,String> formData = Mockito.mock(MultivaluedMap.class);

        EventBuilder eventBuilder= mock(EventBuilder.class);

        Map<String,String> config = new HashMap<>();
        config.put(EmailValidatorFactory.PROVIDER_REGEX,".*@test.tld");

        when(httpRequest.getDecodedFormParameters()).thenReturn(formData);
        when(eventBuilder.detail(Details.REGISTER_METHOD, "form")).thenReturn(eventBuilder);
        when(context.getEvent()).thenReturn(eventBuilder);
        when(context.getHttpRequest()).thenReturn(httpRequest);
        when(userProfileAttributes.getFirstAttribute(UserModel.EMAIL)).thenReturn("max.mustermann@test.tld");
        when(userProfile.getAttributes()).thenReturn(userProfileAttributes);
        when(AttributeFormDataProcessor.toUserProfile(formData)).thenReturn(userProfile);
        when(context.getAuthenticatorConfig()).thenReturn(authenticatorConfigModel);
        when(authenticatorConfigModel.getConfig()).thenReturn(config);


        EmailValidator emailValidator = new EmailValidator();
        emailValidator.validate(context);

        verify(context,times(1)).error("email not allowed");
        mocked.close();

    }

    @Test
    public void testValidEmail(){

        AttributeUserProfile userProfile = Mockito.mock(AttributeUserProfile.class);
        UserProfileAttributes userProfileAttributes = Mockito.mock(UserProfileAttributes.class);
        HttpRequest httpRequest = Mockito.mock(HttpRequest.class);
        ValidationContext context = Mockito.mock(ValidationContext.class);
        AuthenticatorConfigModel authenticatorConfigModel = Mockito.mock(AuthenticatorConfigModel.class);

        MockedStatic<AttributeFormDataProcessor> mocked = mockStatic(AttributeFormDataProcessor.class);

        MultivaluedMap<String,String> formData = Mockito.mock(MultivaluedMap.class);

        EventBuilder eventBuilder= mock(EventBuilder.class);

        Map<String,String> config = new HashMap<>();
        config.put(EmailValidatorFactory.PROVIDER_REGEX,".*@test.tld");

        when(httpRequest.getDecodedFormParameters()).thenReturn(formData);
        when(eventBuilder.detail(Details.REGISTER_METHOD, "form")).thenReturn(eventBuilder);
        when(context.getEvent()).thenReturn(eventBuilder);
        when(context.getHttpRequest()).thenReturn(httpRequest);
        when(userProfileAttributes.getFirstAttribute(UserModel.EMAIL)).thenReturn("max.mustermann@example.tld");
        when(userProfile.getAttributes()).thenReturn(userProfileAttributes);
        when(AttributeFormDataProcessor.toUserProfile(formData)).thenReturn(userProfile);
        when(context.getAuthenticatorConfig()).thenReturn(authenticatorConfigModel);
        when(authenticatorConfigModel.getConfig()).thenReturn(config);


        EmailValidator emailValidator = new EmailValidator();
        emailValidator.validate(context);

        verify(context,times(1)).success();
        mocked.close();
    }

}
