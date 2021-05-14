package main.java.hr.java.covidportal.model;

import lombok.*;

/**
 * Apstrakna klasa koju nasljeđuje svaka klasa koja prima parametar objekt klase tipa String
 */
@EqualsAndHashCode()
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public abstract class ImenovaniEntitet {

    String naziv;
}
