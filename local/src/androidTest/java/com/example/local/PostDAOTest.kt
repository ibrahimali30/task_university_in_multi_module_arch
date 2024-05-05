package com.example.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.local.database.AppDatabase
import com.example.local.database.UniversityDAO
import com.google.common.truth.Truth
import com.example.local.utils.TestDataGenerator
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class PostDAOTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database : AppDatabase
    private lateinit var universityDao : UniversityDAO

    @Before
    fun setUp() {
        hiltRule.inject()
        universityDao = database.universityDao()
    }

    @After
    fun tearDown() {
        database.close()
    }


    @Test
    fun test_add_university_item_success() = runBlockingTest {

        val item = TestDataGenerator.generateUniversityItem()

        universityDao.addUniversityItem(item)

        val items = universityDao.getUniversityItems()

        Truth.assertThat(items).contains(item)

    }

    @Test
    fun test_get_university_item_success() = runBlockingTest {

        val item = TestDataGenerator.generateUniversityItem()

        universityDao.addUniversityItem(item)

        val result = universityDao.getUniversityItem(item.name)

        Truth.assertThat(item).isEqualTo(result)
    }

    @Test
    fun test_add_and_get_university_items_success() = runBlockingTest {

        val item = TestDataGenerator.generateUniversityItem()

        universityDao.addUniversityItem(item)

        val result = universityDao.getUniversityItems()

        Truth.assertThat(result).containsExactly(item)

    }

    @Test
    fun test_update_university_item_success() = runBlockingTest {

        val item = TestDataGenerator.generateUniversityItem()

        universityDao.addUniversityItem(item)

        val dbItem = universityDao.getUniversityItem(item.name)

        Truth.assertThat(item).isEqualTo(dbItem)

        val updatedItem = item.copy(name = "updated name")

        universityDao.updateUniversityItem(updatedItem)

        val result = universityDao.getUniversityItem(updatedItem.name)

        Truth.assertThat(updatedItem).isEqualTo(result)

    }


    @Test
    fun test_clear_all_posts_success() = runBlockingTest {

        val items = TestDataGenerator.generateUniversityItems()

        universityDao.addUniversityItems(items)

        val dbItems = universityDao.getUniversityItems()

        Truth.assertThat(dbItems).containsExactlyElementsIn(items)

        universityDao.clearCachedUniversityItems()

        val result = universityDao.getUniversityItems()

        Truth.assertThat(result).isEmpty()
    }
}