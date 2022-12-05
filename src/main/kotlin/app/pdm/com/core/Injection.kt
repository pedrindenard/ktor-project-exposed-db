package app.pdm.com.core

import app.pdm.com.modules.login.repository.LoginRepositoryImpl
import app.pdm.com.modules.login.use_case.LoginUserCase
import app.pdm.com.modules.notes.dao.NotesDao
import app.pdm.com.modules.notes.dao.NotesDaoImpl
import app.pdm.com.modules.notes.repository.NotesRepository
import app.pdm.com.modules.notes.repository.NotesRepositoryImpl
import app.pdm.com.modules.notes.use_case.*
import app.pdm.com.modules.users.dao.UsersDao
import app.pdm.com.modules.users.dao.UsersDaoImpl
import app.pdm.com.modules.users.repository.UsersRepository
import app.pdm.com.modules.users.repository.UsersRepositoryImpl
import app.pdm.com.modules.users.use_case.AddUserUseCase
import app.pdm.com.modules.users.use_case.DeleteUserUserCase
import app.pdm.com.modules.users.use_case.EditUserUseCase
import app.pdm.com.modules.users.use_case.GetUserUseCase

object Injection {

    /* ----- DATABASES ----- */
    private val usersDao: UsersDao = UsersDaoImpl()
    private val notesDao: NotesDao = NotesDaoImpl()

    /* ----- REPOSITORIES ----- */
    private val usersRepository: UsersRepository = UsersRepositoryImpl(usersDao)
    private val loginRepository: LoginRepositoryImpl = LoginRepositoryImpl(usersDao)
    private val notesRepository: NotesRepository = NotesRepositoryImpl(notesDao)

    /* ----- USERS USE CASES ----- */
    val addUserUseCase: AddUserUseCase = AddUserUseCase(usersRepository)
    val editUserUseCase: EditUserUseCase = EditUserUseCase(usersRepository)
    val getUserUseCase: GetUserUseCase = GetUserUseCase(usersRepository)
    val deleteUserUseCase: DeleteUserUserCase = DeleteUserUserCase(usersRepository)

    /* ----- LOGIN USE CASES ----- */
    val loginUseCase: LoginUserCase = LoginUserCase(loginRepository)

    /* ----- NOTES USE CASES ----- */
    val addNoteUseCase: AddNoteUseCase = AddNoteUseCase(notesRepository)
    val editNoteUseCase: EditNoteUseCase = EditNoteUseCase(notesRepository)
    val getNoteUseCase: GetNoteUseCase = GetNoteUseCase(notesRepository)
    val getAllNotesUseCase: GetAllNoteUseCase = GetAllNoteUseCase(notesRepository)
    val deleteNoteUseCase: DeleteNoteUserCase = DeleteNoteUserCase(notesRepository)
}