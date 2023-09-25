package br.edu.unifalmg.service;

import br.edu.unifalmg.domain.Chore;
import br.edu.unifalmg.exception.DuplicatedChoreException;
import br.edu.unifalmg.exception.InvalidDeadlineException;
import br.edu.unifalmg.exception.InvalidDescriptionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChoreServiceTest {
    private ChoreService service;
    @BeforeEach
    public void setUp(){
        service = new ChoreService();
    }

    @Test
    @DisplayName("#addChore > Chore when the description is invalid throw an exception")
    void addChoreWhenTheDescriptionIsInvalidThrowAnException() {
        assertAll(
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore(null, null)),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore("", null)),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore(null, LocalDate.now().plusDays(1))),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore("", LocalDate.now().plusDays(1))),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore(null, LocalDate.now().minusDays(1))),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore("", LocalDate.now().minusDays(1)))
        );
    }

    @Test
    @DisplayName("#addChore > When the deadline is invalid > Throw an exception")
    void addChoreWhenTheDeadlineIsInvalidThrowAnException() {
        assertAll(
                () -> assertThrows(InvalidDeadlineException.class,
                        () -> service.addChore("Description", null)),
                () -> assertThrows(InvalidDeadlineException.class,
                        () -> service.addChore("Description", LocalDate.now().minusDays(1)))
        );
    }

    @Test
    @DisplayName("#addChore > When adding a chore > When the chore already exists > Throw an exception")
    void addChoreWhenAddingAChoreWhenTheChoreAlreadyExistsThrowAnException() {
        service.addChore("Description", LocalDate.now());
        assertThrows(DuplicatedChoreException.class,
                () -> service.addChore("Description", LocalDate.now()));
    }

    @Test
    @DisplayName("#addChore > When adding a single chore compare the results")
    void addChoreWhenAddingASingleChoreCompareTheResults() {
        String description = "Description Test";
        LocalDate date = LocalDate.of(2023,10,29);
        service.addChore(description, date);
        assertAll(
                ()-> assertEquals(description,service.getChores().get(0).getDescription()),
                ()-> assertEquals(date,service.getChores().get(0).getDeadline())
        );
    }

    @Test
    @DisplayName("#addChore > When adding more than one chore compare the results")
    void addChoreWhenAddingMoreThanOneChoreCompareTheResults() {

        String description = "Description Test";
        LocalDate date = LocalDate.of(2023,12,25);

        String description2 = "Description Test 2";
        LocalDate date2 = LocalDate.of(2024,1,1);

        service.addChore(description, date);
        service.addChore(description2,date2);

        assertAll(
                ()-> assertEquals(description,service.getChores().get(0).getDescription()),
                ()-> assertEquals(date,service.getChores().get(0).getDeadline()),
                ()->assertEquals(description2,service.getChores().get(1).getDescription()),
                ()->assertEquals(date2,service.getChores().get(1).getDeadline())
        );
    }




}
