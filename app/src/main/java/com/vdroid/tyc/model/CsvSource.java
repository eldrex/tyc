package com.vdroid.tyc.model;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;


// anglicke_slovo;moznost_1;moznost_2;moznost_3;moznost_4;index_spravnej_odpovede;
public class CsvSource {

    public static CsvContainer load(Context context, Uri csvUri) throws IOException {

        final CsvReader csvReader = new CsvReader();
        csvReader.setContainsHeader(false);
        csvReader.setFieldSeparator(';');
        //csvReader.setTextDelimiter('\'');

        InputStream csvStream = context.getContentResolver().openInputStream(csvUri);
        InputStreamReader sr = new InputStreamReader(csvStream);

        return csvReader.read(sr);
    }

}
