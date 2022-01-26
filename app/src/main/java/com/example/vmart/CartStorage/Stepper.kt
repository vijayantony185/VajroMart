package com.example.vmart.CartStorage

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.vmart.Interfaces.StepperInterface
import com.example.vmart.R

 class Stepper : ConstraintLayout {
     var currentValue = 1
     var productId = 0
     var position = 0
    var stepperInterFace: StepperInterface? = null
    var img_plus: ImageView? = null
    var img_minus: ImageView? = null
    var txt_count : TextView? = null

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    fun init() {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.layout_cart_stepper, this, true)

        img_plus = view.findViewById(R.id.img_plus)
        img_minus = view.findViewById(R.id.img_minus)
        txt_count = view.findViewById(R.id.txt_count)

        img_plus!!.setOnClickListener(OnClickListener { updateValue(1) })

        img_plus!!.setOnLongClickListener(OnLongClickListener {
            updateValue(3)
            true
        })

        img_minus!!.setOnClickListener(OnClickListener { updateValue(-1) })

        img_minus!!.setOnLongClickListener(OnLongClickListener {
            updateValue(-3)
            true
        })

        img_plus!!.setLongClickable(true)
        img_minus!!.setLongClickable(true)

        if (currentValue <= 0) {
            currentValue = 0
        } else {
            txt_count!!.visibility = VISIBLE
            img_plus!!.visibility = VISIBLE
            img_minus!!.visibility = VISIBLE
        }

        updateText()
    }

    fun update(clickListener: OnClickListener) {
        img_plus!!.setOnClickListener(OnClickListener { v -> clickListener.onClick(v) })
        img_minus!!.setOnClickListener(OnClickListener { v -> clickListener.onClick(v) })
    }

    private fun updateValue(value: Int) {
        currentValue += value
        if (currentValue <= 0) {
            currentValue = 0
        } else {
            txt_count!!.visibility = VISIBLE
            img_plus!!.visibility = VISIBLE
            img_minus!!.visibility = VISIBLE
        }
        updateText()
        if (stepperInterFace != null) {
            stepperInterFace?.didChanged(position, productId, currentValue)
        }
    }

    fun setCurrentValue(value: Int, stock: Int) {
        if (currentValue < 0) {
            currentValue = 0
        }
        if (value > stock) {
            img_plus!!.visibility = View.GONE
        } else {
            img_plus!!.visibility = View.VISIBLE
            currentValue = value
            updateText()
        }
        Log.d("CurrentValue", "---------------->$currentValue")
    }


    private fun updateText() {
        txt_count!!.setText(currentValue.toString() + "")
    }

}