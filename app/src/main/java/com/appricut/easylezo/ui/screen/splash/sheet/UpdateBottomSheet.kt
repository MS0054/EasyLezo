package com.appricut.easylezo.ui.screen.splash.sheet

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.appricut.easylezo.core.domain.model.UpdateResult
import com.appricut.easylezo.core.domain.model.UpdateType
import com.appricut.easylezo.ui.component.LanguageAwareText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBottomSheet(
    updateResult: UpdateResult,
    onUpdateClick: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val type by remember { mutableStateOf(updateResult.type) }
    val info by remember { mutableStateOf(updateResult.info) }
    val sheetState = rememberModalBottomSheetState (
        // اگر اجباری بود، کاربر نتواند با کشیدن به پایین آن را ببندد
        confirmValueChange = { type != UpdateType.FORCE }
    )

    ModalBottomSheet(
        onDismissRequest = { if (type != UpdateType.FORCE) onDismiss() },
        sheetState = sheetState,
        // حذف خط تیره بالای باتم شیت در حالت اجباری (اختیاری)
        dragHandle = if (type == UpdateType.FORCE) null else { { BottomSheetDefaults.DragHandle() } },
        scrimColor = Color.Black.copy(alpha = 0.5f)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .navigationBarsPadding(), // رعایت فاصله دکمه‌های پایین گوشی
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LanguageAwareText(
                text = if (type == UpdateType.FORCE) "بروزرسانی ضروری" else "نسخه جدید موجود است",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // نمایش ورژن جدید
            Surface (
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            ) {
                LanguageAwareText(
                    text = "نسخه ${info.latestVersion}",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // بخش لیست تغییرات
            LanguageAwareText(
                text = "تغییرات این نسخه:",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            LanguageAwareText(
                text = info.releaseNotes,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // دکمه‌های عملیاتی
            Button (
                onClick = { onUpdateClick(info.updateUrl) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                LanguageAwareText("دریافت نسخه جدید")
            }

            if (type != UpdateType.FORCE) {
                TextButton (
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LanguageAwareText("بعداً یادآوری کن", color = Color.Gray)
                }
            } else {
                // فاصله اضافی برای حالت اجباری که دکمه انصراف ندارد
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // جلوگیری از بستن با دکمه Back گوشی در حالت اجباری
    BackHandler (enabled = type == UpdateType.FORCE) {
        // هیچ کاری انجام نمی‌دهد (مانع بستن می‌شود)
    }
}