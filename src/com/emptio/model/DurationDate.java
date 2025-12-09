package com.emptio.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DurationDate implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDate dateOfEstablishment;
    private LocalDate dateOfEnd;
    private static final List<DurationDate> extent = new ArrayList<>();

    public DurationDate(LocalDate start, LocalDate end) {
        if (start == null || end == null)
            throw new IllegalArgumentException("Dates cannot be null");
        if (end.isBefore(start))
            throw new IllegalArgumentException("End date cannot be before start date");

        this.dateOfEstablishment = start;
        this.dateOfEnd = end;
    }

    public long TimeLeft() {
        return LocalDate.now().until(dateOfEnd).getDays();
    }

    public long getDaysTotal() {
        return dateOfEstablishment.until(dateOfEnd).getDays();
    }

    public static List<DurationDate> getExtentForSave() {
        return new ArrayList<>(extent);
    }

    public static void loadExtent(List<DurationDate> loaded) {
        extent.clear();
        if (loaded != null) extent.addAll(loaded);
    }
}
