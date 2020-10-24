package com.franciscociecursoandroid.uberclone;

import com.franciscociecursoandroid.uberclone.model.User;
import com.franciscociecursoandroid.uberclone.model.UserType;

import junit.framework.TestCase;

public class PessoaTeste extends TestCase {

    public void testeTipoDeUsuarioComString() {
        User u = new User();
        try {
            u.setType("MOTORISTA");
        } catch (Exception e) {

        }
        assertEquals(u.getType(), UserType.MOTORISTA.toString());
    }
}
