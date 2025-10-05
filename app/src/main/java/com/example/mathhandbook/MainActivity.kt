package com.example.mathhandbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathHandbookTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MathHandbookApp()
                }
            }
        }
    }
}

data class MathConcept(
    val id: Int,
    val title: String,
    val description: String,
    val formula: String,
    val category: String
)

val mathConcepts = listOf(
    MathConcept(
        1,
        "Теорема Пифагора",
        "Фундаментальная теорема евклидовой геометрии, устанавливающая соотношение между сторонами прямоугольного треугольника",
        "a² + b² = c²",
        "Геометрия"
    ),
    MathConcept(
        2,
        "Квадратное уравнение",
        "Уравнение второй степени с одним неизвестным",
        "ax² + bx + c = 0",
        "Алгебра"
    ),
    MathConcept(
        3,
        "Площадь круга",
        "Формула для вычисления площади круга через радиус",
        "S = πr²",
        "Геометрия"
    ),
    MathConcept(
        4,
        "Производная",
        "Основное понятие дифференциального исчисления, характеризующее скорость изменения функции",
        "f'(x) = lim(h→0) [f(x+h) - f(x)]/h",
        "Математический анализ"
    ),
    MathConcept(
        5,
        "Интеграл",
        "Основное понятие интегрального исчисления, обобщение понятия суммы",
        "∫f(x)dx",
        "Математический анализ"
    ),
    MathConcept(
        6,
        "Синус",
        "Тригонометрическая функция, отношение противолежащего катета к гипотенузе",
        "sin(θ) = противолежащий/гипотенуза",
        "Тригонометрия"
    ),
    MathConcept(
        7,
        "Косинус",
        "Тригонометрическая функция, отношение прилежащего катета к гипотенузе",
        "cos(θ) = прилежащий/гипотенуза",
        "Тригонометрия"
    ),
    MathConcept(
        8,
        "Логарифм",
        "Обратная функция к exponentation, показывает степень в которую нужно возвести основание",
        "logₐ(b) = c ⇔ aᶜ = b",
        "Алгебра"
    ),
    MathConcept(
        9,
        "Тангенс",
        "Тригонометрическая функция, отношение синуса к косинусу",
        "tan(θ) = sin(θ)/cos(θ)",
        "Тригонометрия"
    ),
    MathConcept(
        10,
        "Дискриминант",
        "Выражение, определяющее количество корней квадратного уравнения",
        "D = b² - 4ac",
        "Алгебра"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MathHandbookApp() {
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Все") }

    val categories = listOf("Все") + mathConcepts.map { it.category }.distinct()


    val filteredConcepts = mathConcepts.filter { concept ->
        (concept.title.contains(searchText, ignoreCase = true) ||
                concept.description.contains(searchText, ignoreCase = true) ||
                concept.formula.contains(searchText, ignoreCase = true)) &&
                (selectedCategory == "Все" || concept.category == selectedCategory)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        
        Text(
            text = "Математический Справочник",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            placeholder = { Text("Поиск по названию, описанию или формуле...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Поиск"
                )
            },
            singleLine = true
        )

        
        Text(
            text = "Категории:",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        
        ScrollableTabRow(
            selectedTabIndex = categories.indexOf(selectedCategory),
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            edgePadding = 0.dp
        ) {
            categories.forEach { category ->
                Tab(
                    selected = category == selectedCategory,
                    onClick = { selectedCategory = category },
                    text = {
                        Text(
                            text = category,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        
        Text(
            text = "Найдено: ${filteredConcepts.size}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        
        if (filteredConcepts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "😕",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "Ничего не найдено",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Попробуйте изменить запрос или выбрать другую категорию",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filteredConcepts) { concept ->
                    MathConceptCard(concept = concept)
                }
            }
        }
    }
}

@Composable
fun MathConceptCard(concept: MathConcept) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            
            Text(
                text = concept.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            
            Text(
                text = concept.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            
            Text(
                text = concept.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            
            Text(
                text = "Формула:",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = concept.formula,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}


private val MathHandbookColorScheme = lightColorScheme(
    primary = Color(0xFF2E7D32), // Зеленый
    secondary = Color(0xFF1976D2), // Синий
    surface = Color(0xFFFFFFFF), // Белый
    surfaceVariant = Color(0xFFE8F5E9), // Светло-зеленый
    background = Color(0xFFF5F5F5), // Светло-серый
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),
    onBackground = Color(0xFF000000)
)

@Composable
fun MathHandbookTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MathHandbookColorScheme,
        typography = Typography(),
        content = content
    )
}
