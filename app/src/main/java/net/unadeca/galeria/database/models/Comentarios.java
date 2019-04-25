package net.unadeca.galeria.Activities.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import net.unadeca.galeria.Activities.database.GaleriaDB;
import net.unadeca.galeria.Activities.database.models.Imagenes;

@Table(database = GaleriaDB.class)
public class Comentarios extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String comentario;

    @Column
    @ForeignKey(tableClass = Imagenes.class)
    public Imagenes imagen;
}
