package com.example.rentcarapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rentcarapp.R;
import com.example.rentcarapp.data.AppDatabase;
import com.example.rentcarapp.data.entity.Customer;

import java.util.regex.Pattern;

public class AddClientFragment extends Fragment {

    private EditText fullNameEditText, addressEditText, phoneEditText, emailEditText;
    private Button addButton;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_client, container, false);

        db = AppDatabase.getInstance(getContext());

        fullNameEditText = view.findViewById(R.id.full_name_edit_text);
        addressEditText = view.findViewById(R.id.address_edit_text);
        phoneEditText = view.findViewById(R.id.phone_edit_text);
        emailEditText = view.findViewById(R.id.email_edit_text);
        addButton = view.findViewById(R.id.add_button);

        fullNameEditText.setFilters(new InputFilter[]{
                (source, start, end, dest, dstart, dend) -> {
                    if (source != null && Pattern.compile("[0-9]").matcher(source).find()) {
                        return "";
                    }
                    return null;
                }
        });

        phoneEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
        phoneEditText.addTextChangedListener(new PhoneNumberTextWatcher(phoneEditText));

        addButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString();
            String address = addressEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String email = emailEditText.getText().toString();

            if (!fullName.isEmpty() && !address.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
                new Thread(() -> {
                    Customer customer = new Customer();
                    customer.fullName = fullName;
                    customer.address = address;
                    customer.phone = phone;
                    customer.email = email;

                    db.customerDao().insertCustomer(customer);

                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Клиент добавлен!", Toast.LENGTH_SHORT).show();
                        fullNameEditText.setText("");
                        addressEditText.setText("");
                        phoneEditText.setText("+7");
                        emailEditText.setText("");
                        hideKeyboard();
                    });
                }).start();
            } else {
                Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private class PhoneNumberTextWatcher implements TextWatcher {

        private EditText editText;
        private boolean isUpdating;

        public PhoneNumberTextWatcher(EditText editText) {
            this.editText = editText;
            this.isUpdating = false;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (isUpdating) {
                return;
            }

            isUpdating = true;

            String formattedPhone = formatPhoneNumber(s.toString());
            editText.setText(formattedPhone);
            editText.setSelection(formattedPhone.length());

            isUpdating = false;
        }

        private String formatPhoneNumber(String phone) {
            String digits = phone.replaceAll("[^\\d]", "");

            if (digits.length() > 1 && !digits.startsWith("7")) {
                digits = "7" + digits.substring(1);
            }

            if (digits.length() >= 1) {
                phone = "+7";
                if (digits.length() > 1) {
                    phone += " (" + digits.substring(1, Math.min(digits.length(), 4)) + ")";
                }
                if (digits.length() > 4) {
                    phone += " " + digits.substring(4, Math.min(digits.length(), 7));
                }
                if (digits.length() > 7) {
                    phone += "-" + digits.substring(7, Math.min(digits.length(), 9));
                }
                if (digits.length() > 9) {
                    phone += "-" + digits.substring(9, Math.min(digits.length(), 11));
                }
            }
            return phone;
        }
    }
}