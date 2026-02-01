package com.example.alwanyapp.Presentation.auth.Test;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alwanyapp.Domain.Question;
import com.example.alwanyapp.R;
import com.example.alwanyapp.Util.Common.CommonData;
import com.example.alwanyapp.databinding.FragmentTestBinding;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TestFragment extends Fragment {
    private FragmentTestBinding binding;

    private int timer;
    private int number = 0;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTestBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    CountDownTimer countDownTimer;

    private void timer() {
        timer = 59;
        countDownTimer = new CountDownTimer(59000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                binding.timer.setText("00:" + timer);
                timer--;
            }

            public void onFinish() {
                binding.timer.setText("إنتهى الوقت");
            }
        }.start();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        SweetAlertDialog warning = new SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE);
        warning.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        warning.setTitleText(getString(R.string.test_alert));
        warning.setConfirmText("حسنا");
        warning.setCancelable(false);
        warning.setCanceledOnTouchOutside(false);
        warning.show();
        warning.setConfirmClickListener(sweetAlertDialog -> {
            setDataToViews(number);
            warning.dismiss();
        });
        binding.optionOne.setOnClickListener(v -> {
            setDataToViews(number);
            countDownTimer.cancel();
            CommonData.answerOne += 1;
        });
        binding.optionTwo.setOnClickListener(v -> {
            setDataToViews(number);
            countDownTimer.cancel();
            CommonData.answerTwo += 1;
        });
        binding.optionThree.setOnClickListener(v -> {
            setDataToViews(number);
            countDownTimer.cancel();
            CommonData.answerThree += 1;
        });
        binding.optionFour.setOnClickListener(v -> {
            setDataToViews(number);
            countDownTimer.cancel();
            CommonData.answerFour += 1;
        });
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void setDataToViews(int index) {
        //index = number = 0
        if (number <= 9) {
            binding.questionNumber.setText(getQuestion().get(index).getTitle());
            binding.question.setText("أختر الصورة التي تحتوي على " + getQuestion().get(index).getQuestion());
            binding.optionOne.setImageDrawable(getResources().getDrawable(getQuestion().get(index).getAnswerOne()));
            binding.optionTwo.setImageDrawable(getResources().getDrawable(getQuestion().get(index).getAnswerTwo()));
            binding.optionThree.setImageDrawable(getResources().getDrawable(getQuestion().get(index).getAnswerThree()));
            binding.optionFour.setImageDrawable(getResources().getDrawable(getQuestion().get(index).getAnswerFour()));
            number++;
            Log.e("TAG", "setDataToViews: " + number);
            //number = 1
        } else {
            Log.e("this",number+"");
            countDownTimer.cancel();
            showResult();
        }
        timer();
    }

    @Override
    public void onStop() {
        super.onStop();
        number = 0;
        countDownTimer.cancel();
    }

    private List<Question> getQuestion() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1, R.drawable.apple1, R.drawable.apple2, R.drawable.apple3, R.drawable.apple4, "السؤال الأول", "التفاحة الحمراء:"));
        questions.add(new Question(2, R.drawable.lemon5, R.drawable.lemon6, R.drawable.lemon7, R.drawable.lemon8, "السؤال الثاني", "الليمون الأخضر:"));
        questions.add(new Question(3, R.drawable.grapes9, R.drawable.grapes10, R.drawable.grapes11, R.drawable.grapes12, "السؤال الثالث", "العنب البنفسجي:"));
        questions.add(new Question(4, R.drawable.lemon13, R.drawable.lemon14, R.drawable.lemon15, R.drawable.lemon16, "السؤال الرابع", "الليمون البرتقالي:"));
        questions.add(new Question(5, R.drawable.strawberry17, R.drawable.strawberry18, R.drawable.strawberry18, R.drawable.strawberry20, "السؤال الخامس", "الفراولة الحمراء:"));
        questions.add(new Question(6, R.drawable.lemon21, R.drawable.lemon22, R.drawable.lemon23, R.drawable.lemon24, "السؤال السادس", "الليمون الأصفر:"));
        questions.add(new Question(7, R.drawable.apple25, R.drawable.apple26, R.drawable.apple27, R.drawable.apple28, "السؤال السابع", "التفاحة الخضراء:"));
        questions.add(new Question(8, R.drawable.apricot29, R.drawable.apricot30, R.drawable.apricot31, R.drawable.apricot32, "السؤال الثامن", "المشمش البرتقالي:"));
        questions.add(new Question(9, R.drawable.grapes33, R.drawable.grapes34, R.drawable.grapes35, R.drawable.grapes36, "السؤال التاسع", "التوت البنفسجي:"));
        questions.add(new Question(10, R.drawable.apple37, R.drawable.apple38, R.drawable.apple39, R.drawable.apple40, "السؤال العاشر", "التفاحة الصفراء:"));
        return questions;
    }

    private void showResult() {
        Navigation.findNavController(view).navigate(R.id.action_nav_test_to_result);

    }

}