import com.abisoft.todocompose.MainActivity
import com.abisoft.todocompose.di.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface MyAppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(todoScreen: TodoViewModel)
}
