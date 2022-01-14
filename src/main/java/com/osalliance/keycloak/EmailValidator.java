package com.osalliance.keycloak;

import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.events.Details;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.services.messages.Messages;
import org.keycloak.services.validation.Validation;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

public class EmailValidator implements FormAction {
    public void buildPage(FormContext context, LoginFormsProvider form) {

    }

    public void validate(ValidationContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();

        String email = formData.getFirst(UserModel.EMAIL);

        context.getEvent().detail(Details.REGISTER_METHOD, "form");

        if(context.getAuthenticatorConfig().getConfig().containsKey(EmailValidatorFactory.PROVIDER_REGEX)){
            String regex = context.getAuthenticatorConfig().getConfig().get(EmailValidatorFactory.PROVIDER_REGEX);

            List<FormMessage> errors = new ArrayList<FormMessage>();

            if(email.matches(regex)){
                context.error("email not allowed");
                errors.add(new FormMessage(Validation.FIELD_EMAIL,Messages.INVALID_EMAIL));
            }else{
                context.success();
            }
            context.validationError(formData, errors);
            return;
        }

        context.success();

    }

    public void success(FormContext context) {

    }

    public boolean requiresUser() {
        return false;
    }

    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return false;
    }

    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {

    }

    public void close() {

    }
}
