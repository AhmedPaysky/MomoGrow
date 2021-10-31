package com.paysky.momogrow.views.customviews;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;

import com.paysky.momogrow.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class EditMobileNumber extends AppCompatEditText implements TextWatcher {

    private static final char SPACE_CHAR = '-';
    private static final String SPACE_STRING = String.valueOf(SPACE_CHAR);
    private static final int GROUPSIZE = 3;
    private boolean isUpdating = false;

    Context context;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public EditMobileNumber(Context context) {
        super(context);
        this.context = context;
        addTextWatcher();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public EditMobileNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        addTextWatcher();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public EditMobileNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        addTextWatcher();
    }


    private void addTextWatcher() {
        addTextChangedListener(this);
    }

    public String getMobileNumber() {
        return getText().toString().replace(" ", "").trim();
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String originalString = s.toString();

        // Check if we are already updating, to avoid infinite loop.
        // Also check if the string is already in a valid format.
        String regexp = "^(\\d{3}\\s)*\\d{0,3}(?<!\\s)$";
        if (isUpdating || originalString.matches(regexp)) {
            return;
        }

        // Set flag to indicate that we are updating the Editable.
        isUpdating = true;

        // First all whitespaces must be removed. Find the index of all whitespace.
        LinkedList<Integer> spaceIndices = new LinkedList<>();
        for (int index = originalString.indexOf(SPACE_CHAR); index >= 0; index = originalString.indexOf(SPACE_CHAR, index + 1)) {
            spaceIndices.offerLast(index);
        }

        // Delete the whitespace, starting from the end of the string and working towards the beginning.
        Integer spaceIndex;
        while (!spaceIndices.isEmpty()) {
            spaceIndex = spaceIndices.removeLast();
            s.delete(spaceIndex, spaceIndex + 1);
        }

        // Loop through the string again and add whitespaces in the correct positions
        for (int i = 0; ((i + 1) * GROUPSIZE + i) < s.length(); i++) {
            s.insert((i + 1) * GROUPSIZE + i, SPACE_STRING);
        }

        // Finally check that the cursor is not placed before a whitespace.
        // This will happen if, for example, the user deleted the digit '5' in
        // the string: "1234 567".
        // If it is, move it back one step; otherwise it will be impossible to delete
        // further numbers.
        int cursorPos = getSelectionStart();
        if (cursorPos > 0 && s.charAt(cursorPos - 1) == SPACE_CHAR) {
            setSelection(cursorPos - 1);
        }

        isUpdating = false;

    }
}
