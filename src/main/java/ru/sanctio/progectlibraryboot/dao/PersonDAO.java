package ru.sanctio.progectlibraryboot.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.sanctio.progectlibraryboot.models.Book;
import ru.sanctio.progectlibraryboot.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                        .stream().findAny().orElse(null);
    }

    //для валидации уникальности ФИО
    public Optional<Person> getPersonByFullName(String fio) {
        return jdbcTemplate.query("SELECT * FROM person WHERE fio = ?", new Object[]{fio},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (fio, yearOfBirth) VALUES (?,?)", person.getFio(),
                person.getYearOfBirth());
    }

    public void update(Person person) {
        jdbcTemplate.update("UPDATE person SET fio = ?, yearOfBirth = ? WHERE id = ?", person.getFio(),
                person.getYearOfBirth(), person.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

}
