package es.ifp.parking;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.test.core.app.ApplicationProvider;

public class BaseDatosReservasTest {

    private BaseDatosReservas baseDatos;

    @Before
    public void setUp() {
        // Crear un mock del contexto de la aplicación
        Context mockContext = Mockito.mock(Context.class);

        // Crear la instancia de la base de datos utilizando el mock del contexto
        baseDatos = new BaseDatosReservas(mockContext);
    }

    @After
    public void tearDown() {
        // Limpiar la base de datos después de cada prueba si es necesario
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        db.execSQL("DELETE FROM reserva");
        db.close();
    }

    @Test
    public void testInsertReserva() {
        // Configurar el comportamiento del mock para simular la inserción de una reserva
        doNothing().when(baseDatos).insertReserva(anyInt(), anyString(), anyString(), anyDouble(), anyDouble(), anyString());

        // Llamar al método que inserta la reserva
        baseDatos.insertReserva(1, "2023-01-01", "12:00 PM", 0.0, 0.0, "Detalles de la reserva");

        // Verificar que el método se llamó correctamente
        verify(baseDatos).insertReserva(eq(1), eq("2023-01-01"), eq("12:00 PM"), eq(0.0), eq(0.0), eq("Detalles de la reserva"));

        // Verificar que la reserva se haya insertado correctamente (podrías hacer más verificaciones si es necesario)
        int numReservas = baseDatos.numReservas();
        assertEquals(1, numReservas);
    }
}

