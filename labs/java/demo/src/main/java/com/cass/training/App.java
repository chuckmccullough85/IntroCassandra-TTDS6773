package com.cass.training;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.querybuilder.insert.InsertInto;
import com.datastax.oss.driver.api.querybuilder.insert.RegularInsert;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.*;

public class App {
    public static void main(String[] args) {
        // queryBuilder();
        // cqlQuery();
        // insert();
        prepared();
    }
    static void prepared() {
try (CqlSession sess = CqlSession.builder().build()) {
    PreparedStatement ps = sess
        .prepare("insert into data_model_lab.actor_by_name (id, name) values (?,?)");
    BoundStatement bs = ps.bind(811457, "Hank Hill");
    sess.execute(bs);
}
    }

    static void insert() {
        try (CqlSession sess = CqlSession.builder().build()) {
            RegularInsert insert = insertInto("data_model_lab", "actor_by_name")
                .value("name", literal("Rusty Shackleford"))
                .value("id", literal(823456));
            sess.execute(insert.build());
        }
    }


    static void queryBuilder() {
        try (CqlSession sess = CqlSession.builder().build()) {
            SimpleStatement stmt = selectFrom("system", "compaction_history")
                    .column("columnfamily_name")
                    .column("compacted_at").build();
            ResultSet rs = sess.execute(stmt);
            for (Row row : rs) {
                System.out.println(row.getString(0) + ":\t "
                        + row.getInstant(1));
            }
        }
    }

    public static void cqlQuery() {
        try (CqlSession sess = CqlSession.builder().build()) {
            ResultSet rs = sess.execute(
                    "select columnfamily_name, compacted_at from system.compaction_history");
            for (Row row : rs) {
                System.out.println(row.getString(0) + ":\t "
                        + row.getInstant(1));
            }
        }
    }
}
