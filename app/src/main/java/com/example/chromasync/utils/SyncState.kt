package com.example.chromasync.utils

sealed interface SyncState {

    object NotSynced: SyncState

    object SyncSuccess : SyncState

    object Syncing: SyncState

    object SyncError: SyncState
}