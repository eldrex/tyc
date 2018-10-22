package com.vdroid.tyc.model;

import android.content.Context;
import android.net.Uri;

import java.io.InputStream;
import java.io.InputStreamReader;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;


// anglicke_slovo;moznost_1;moznost_2;moznost_3;moznost_4;index_spravnej_odpovede;
public class CsvSource {

    public static CsvContainer load(Context context, Uri csvUri) throws Exception {

        final CsvReader csvReader = new CsvReader();
        csvReader.setContainsHeader(false);
        csvReader.setFieldSeparator(';');
        //csvReader.setTextDelimiter('\'');

        InputStream csvStream = context.getContentResolver().openInputStream(csvUri);
        InputStreamReader sr = new InputStreamReader(csvStream);

        CsvContainer csvContainer = csvReader.read(sr);
        if (csvContainer.getRowCount() == 0) {
            throw new Exception("No entries found in csv");
        }

        return csvContainer;
    }

    public static void checkFirstLine(CsvContainer csvContainer) throws Exception {
        CsvRow row = csvContainer.getRow(0);
        if (row.getFieldCount() != 6) {
            throw new Exception("Invalid csv. Expected 6 fields per row");
        }
    }

}
