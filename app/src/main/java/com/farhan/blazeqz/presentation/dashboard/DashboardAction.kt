package com.farhan.blazeqz.presentation.dashboard

sealed interface DashboardAction {
    data object NameEditIconClick: DashboardAction
    data object NameEditIconDismiss: DashboardAction
    data object NameEditDialogConfirm: DashboardAction
    data class SetUsername(val username: String): DashboardAction
    data object RefreshIconClick: DashboardAction
}