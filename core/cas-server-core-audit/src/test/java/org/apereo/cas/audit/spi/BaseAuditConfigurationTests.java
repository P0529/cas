package org.apereo.cas.audit.spi;

import org.apereo.cas.util.DateTimeUtils;

import lombok.val;
import org.apereo.inspektr.audit.AuditActionContext;
import org.apereo.inspektr.audit.AuditTrailManager;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * This is {@link BaseAuditConfigurationTests}.
 *
 * @author Timur Duehr
 * @since 6.0.0
 */
public abstract class BaseAuditConfigurationTests {
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    public abstract AuditTrailManager getAuditTrailManager();

    @Test
    public void verifyAuditManager() {
        val time = LocalDate.now().minusDays(2);
        val since = DateTimeUtils.dateOf(time);
        val ctx = new AuditActionContext("casuser", "TEST", "TEST",
            "CAS", since, "1.2.3.4",
            "1.2.3.4");
        getAuditTrailManager().record(ctx);
        val results = getAuditTrailManager().getAuditRecordsSince(time);
        assertFalse(results.isEmpty());
    }
}
