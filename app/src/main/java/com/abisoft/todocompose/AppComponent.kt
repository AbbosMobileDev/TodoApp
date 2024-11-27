import com.abisoft.uimodule.ui.viewModel.TodoViewModel
import com.abisoft.uimodule.ui.MainActivity
import com.abisoft.todocompose.di.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface MyAppComponent {
    fun inject(mainActivity: com.abisoft.uimodule.ui.MainActivity)
    fun inject(todoScreen: com.abisoft.uimodule.ui.viewModel.TodoViewModel)
}
