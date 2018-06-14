package org.incode.module.base.dom.with;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Disabled;

public interface WithStartDate {

    @Disabled
    public LocalDate getStartDate();
    public void setStartDate(LocalDate startDate);

}
