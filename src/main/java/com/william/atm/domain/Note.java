package com.william.atm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Note object representing any number of a unique banknote.
 *
 * @author William Barrett
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table (name="notes")
public class Note implements Comparable<Note>{
    @Id private int id;
    private int numberOfNotes;

    /**
     * Compare if banknote type i.e 50,20,10 is bigger or smaller
     *
     */
    @Override
    public int compareTo(@NotNull Note o) {
        if(this.id < o.id) return -1;
        if(this.id > o.id) return 1;
        return 0;
    }
}
