package es.itx.prices.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

	private DateUtils() {
		
	}
	
    // Definir el formato de fecha esperado
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Convierte una cadena a un objeto LocalDateTime, según el formato especificado.
     *
     * @param dateString la fecha en formato String, por ejemplo, "2020-06-14T10:00:00".
     * @return un objeto LocalDateTime representando la fecha y hora.
     * @throws IllegalArgumentException si el formato es incorrecto.
     */
    public static LocalDateTime parseDateTime(String dateString) {
        try {
            return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("El formato de la fecha es incorrecto. Debe ser 'yyyy-MM-dd'T'HH:mm:ss'.", e);
        }
    }

    /**
     * Convierte un objeto LocalDateTime a una cadena en el formato especificado.
     *
     * @param dateTime el objeto LocalDateTime.
     * @return una cadena que representa la fecha y hora en el formato 'yyyy-MM-dd'T'HH:mm:ss'.
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }
}