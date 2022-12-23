package com.asd.tecnical_test_asd.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class MessagesHandler {
	public static final String RECORDS_LISTED = "Listado Obtenido.";
	public static final String RECORDS_NOT_LISTED = "Listado no obtenido.";
	public static final String RECORDS_NOT_SPECIAL_SCHEDULE = "No hay registros almacenados";
	public static final String RECORDS_STORED = "Registro Almacenado Correctamente.";
	public static final String RECORDS_NOT_STORED = "Registro No Almacenado.";
	public static final String GENERAL_ERROR = "ERROR GENERAL.";
	public static final String RECORDS_UPDATE = "Registro Actualizado Correctamente.";
	public static final String RECORDS_DELECTED = "Registro Actualizado Correctamente.";
	public static final String RECORDS_NOT_UPDATE = "Registro no Actualizado.";
	public static final String INTERNAL_ERROR = "Se presentó un error.";
	public static final String TOKEN_OK = "Token obtenido";
	public static final String TOKEN_ERROR = "Token no obtenido";
	public static final String DUPLICATE_DATA = "No se permiten registros duplicados";
	public static final String USER_DOES_NOT_EXIST_IN_ACTIVE_DIRECTORY = "Usuario no existe en directorio activo";
	public static final String THE_USER_DOES_NOT_EXIST_IN_DATABASES = "El usuario no existe en la base de datos";
	public static final String NO_SCHEDULE = "Horario Inactivo";
	public static final String NO_SERVICE = "No hay servicio";
	public static final String SCHEDULE = "Horario Activo";
	public static final String USER = " El usuario: ";
	public static final String TRY = " ha intentado iniciar sesión desde la IP: ";
	public static final String TOKEN = "Token invalido";
	public static final String INPUT_VALIDATION = "ERROR: ";
	public static final String VALIDATION_ERROR_MESSAGE = "ERROR DE VALIDACIÓN: {}";
	public static final String UNAUTHORIZED = "Unauthorized";

	public static final int OK = 200;
	public static final int ERROR_NF = 404;
	public static final int ERROR_INTERNAL = 500;
	public static final int ERROR_VALIDATION = 400;
	public static final int ERROR_DUPLICATE = 409;
	public static final int ERROR_TOKEN = 401;
	public static final int ERROR_USER_NOT_EXIST = 403;
	public static final int ERROR_SCHEDULE = 402;
	public static final int ERROR_USER_NOT_EXIST_IN_ACTIVE_DIRECTORY = 404;
	public static final int ERROR_USER_NOT_EXIST_IN_DATABASES = 405;
	public static final int ERROR_NO_SCHEDULE = 406;
	public static final int ERROR_SCHEDULE_ACTIVE = 407;
	public static final int ERROR_SCHEDULE_INACTIVE = 408;
	public static final int ERROR_SCHEDULE_NOT_EXIST = 409;
	public static final int ERROR_SCHEDULE_NOT_EXIST_IN_DATABASES = 410;
	public static final int ERROR_SCHEDULE_NOT_EXIST_IN_ACTIVE_DIRECTORY = 411;


	public static final int ERROR_O = 1;
	public static final int ERROR_B = 2;
	public static final int ERROR_F = 3;

	public static final boolean VALID = true;
	public static final boolean INVALID = false;
	public static final String EXITS_RECORD="Registro fué almacenado previamente.";

	@Autowired
	private MessageSource messageSource;

	private MessageSourceAccessor messageSourceAccesor;

	@PostConstruct
	private void init() {
		messageSourceAccesor = new MessageSourceAccessor(messageSource, Locale.forLanguageTag("es"));
	}

	public String get(String code) {
		return messageSourceAccesor.getMessage(code);
	}
	public String get(String code, String messageDefault) {
		return this.get(code, messageDefault, null);
	}

	public String get(String code, String messageDefault, String locale) {
		String idioma = !StringUtils.isEmpty(locale) ? locale : "es";
		return messageSourceAccesor.getMessage(code, messageDefault, Locale.forLanguageTag(idioma));
	}

	public static String genericMessage(String module) {
		return "No hay " + module + " registrados";
	}
}
