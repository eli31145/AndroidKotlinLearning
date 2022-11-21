package com.example.exercise601_myrecyclerviewapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise601_myrecyclerviewapp.model.EmployeeRole
import com.example.exercise601_myrecyclerviewapp.model.EmployeeUiModel
import com.example.exercise601_myrecyclerviewapp.model.Gender

private val FEMALE_SYMBOL by lazy {
    HtmlCompat.fromHtml("&#9793;", HtmlCompat.FROM_HTML_MODE_LEGACY)
}
private val MALE_SYMBOL by lazy {
    HtmlCompat.fromHtml("&#9794;", HtmlCompat.FROM_HTML_MODE_LEGACY)
}
private const val UNKNOWN_SYMBOL = "?"

class EmployeeViewHolder(containerView: View, private val imageLoader: ImageLoader): RecyclerView.ViewHolder(containerView) {

    private val employeeNameView: TextView
            by lazy { containerView.findViewById(R.id.tv_employee_name) }
    private val employeeRoleView: TextView
            by lazy { containerView.findViewById(R.id.tv_employee_role) }
    private val employeeBioView: TextView
            by lazy { containerView.findViewById(R.id.tv_employee_biography) }
    private val employeeGenderView: TextView
            by lazy { containerView.findViewById(R.id.tv_employee_gender) }
    private val employeePhotoView: ImageView by lazy { containerView.findViewById(R.id.iv_employee_photo) }

    fun bindData(employeeData: EmployeeUiModel){
        imageLoader.loadImage(employeeData.imageUrl, employeePhotoView)
        employeeNameView.text = employeeData.name
        employeeRoleView.text = when (employeeData.role){
            EmployeeRole.HumanResources -> "Human Resources"
            EmployeeRole.Technology -> "Technology"
            EmployeeRole.Management -> "Management"
        }
        employeeBioView.text = employeeData.biography
        employeeGenderView.text = when (employeeData.gender){
            Gender.Female -> FEMALE_SYMBOL
            Gender.Male -> MALE_SYMBOL
            Gender.Unknown -> UNKNOWN_SYMBOL
        }
    }
}