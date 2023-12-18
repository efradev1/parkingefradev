package es.ifp.parking;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.test.core.app.ApplicationProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BaseDatosReservasTest {

    private BaseDatosReservas baseDatos;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        baseDatos = new BaseDatosReservas(context);
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
        // Insertar una reserva
        baseDatos.insertReserva(1, "2023-01-01", "12:00 PM", 0.0, 0.0, "Detalles de la reserva");

        // Verificar que la reserva se haya insertado correctamente
        int numReservas = baseDatos.numReservas();
        assertEquals(1, numReservas);
    }

    @Test
    public void testDeleteReserva() {
        // Insertar una reserva para luego eliminarla
        baseDatos.insertReserva(1, "2023-01-01", "12:00 PM", 0.0, 0.0, "Detalles de la reserva");
        int idReserva = 1;

        // Eliminar la reserva
        baseDatos.deleteReserva(idReserva);

        // Verificar que la reserva se haya eliminado correctamente
        int numReservas = baseDatos.numReservas();
        assertEquals(0, numReservas);
    }

    @Test
    public void testGetReservaNoExistente() {
        // Obtener una reserva que no existe
        UnaReserva reserva = baseDatos.getReserva(999);

        // Verificar que la reserva sea nula
        assertNull(reserva);
    }

}

