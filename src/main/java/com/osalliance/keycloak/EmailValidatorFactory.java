package com.osalliance.keycloak;

import org.keycloak.Config;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.Collections;
import java.util.List;

import static org.keycloak.provider.ProviderConfigProperty.STRING_TYPE;

public class EmailValidatorFactory implements FormActionFactory {
    public static final String PROVIDER_ID = "email-block-action";
    protected static final String PROVIDER_REGEX = "regex";

    public static final EmailValidator authenticator = new EmailValidator();

    public String getDisplayType() {
        return "Email Block";
    }

    public String getReferenceCategory() {
        return null;
    }

    public boolean isConfigurable() {
        return true;
    }

    private static AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    public boolean isUserSetupAllowed() {
        return false;
    }

    public String getHelpText() {
        return null;
    }

    public List<ProviderConfigProperty> getConfigProperties() {

        ProviderConfigProperty rep = new ProviderConfigProperty();
        rep.setHelpText( "Regex to Block Email");
        rep.setName(PROVIDER_REGEX);
        rep.setLabel("Email Block Regex");
        rep.setType(STRING_TYPE);

        return Collections.singletonList(rep);
    }

    public FormAction create(KeycloakSession session) {
        return authenticator;
    }

    public void init(Config.Scope config) {

    }

    public void postInit(KeycloakSessionFactory factory) {

    }

    public void close() {

    }

    public String getId() {
        return PROVIDER_ID;
    }
}
