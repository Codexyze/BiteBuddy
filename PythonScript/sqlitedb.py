import sqlite3
import os
import csv

DB_NAME = "food_database.db"

def create_database():
    """
    Creates the SQLite database and food table if not exists.
    """
    conn = sqlite3.connect(DB_NAME)
    cursor = conn.cursor()

    cursor.execute('''
    CREATE TABLE IF NOT EXISTS food_table (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        type TEXT,
        foodname TEXT UNIQUE,  -- make foodname unique to avoid duplicates
        pergram REAL,
        calories REAL,
        protein REAL,
        calcium REAL,
        iron REAL,
        magnesium REAL,
        vit_a REAL,
        vit_b12 REAL,
        vit_c REAL,
        vit_d REAL,
        safeInPregnancy INTEGER,
        menstrualSafe INTEGER,
        female_important INTEGER,
        male_important INTEGER
    )
    ''')

    conn.commit()
    conn.close()
    print(f"✅ Database '{DB_NAME}' is ready with table 'food_table'.")


def insert_food(
    type: str,
    foodname: str,
    pergram: float,
    calories: float,
    protein: float,
    calcium: float,
    iron: float,
    magnesium: float,
    vit_a: float,
    vit_b12: float,
    vit_c: float,
    vit_d: float,
    safeInPregnancy: int,
    menstrualSafe: int,
    female_important: int,
    male_important: int
):
    """
    Inserts a new food item into the food_table if not already present.
    """
    if not os.path.exists(DB_NAME):
        print("⚠ Database not found. Creating new database...")
        create_database()

    conn = sqlite3.connect(DB_NAME)
    cursor = conn.cursor()

    try:
        cursor.execute('''
        INSERT OR IGNORE INTO food_table (
            type, foodname, pergram, calories, protein, calcium, iron, magnesium,
            vit_a, vit_b12, vit_c, vit_d,
            safeInPregnancy, menstrualSafe, female_important, male_important
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        ''', (
            type, foodname, pergram, calories, protein, calcium, iron, magnesium,
            vit_a, vit_b12, vit_c, vit_d,
            safeInPregnancy, menstrualSafe, female_important, male_important
        ))
        conn.commit()
        if cursor.rowcount > 0:
            print(f"✅ Added '{foodname}' successfully.")
        else:
            print(f"⚠ Skipped duplicate '{foodname}'.")
    except Exception as e:
        print(f"❌ Error inserting {foodname}: {e}")
    finally:
        conn.close()


def import_from_csv(csv_file_path):
    """
    Reads food data from CSV and inserts it into the database.
    """
    create_database()  # ensure table exists

    with open(csv_file_path, newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            insert_food(
                row["type"],
                row["foodname"],
                float(row["pergram"]),
                float(row["calories"]),
                float(row["protein"]),
                float(row["calcium"]),
                float(row["iron"]),
                float(row["magnesium"]),
                float(row["vit_a"]),
                float(row["vit_b12"]),
                float(row["vit_c"]),
                float(row["vit_d"]),
                int(row["safeInPregnancy"]),
                int(row["menstrualSafe"]),
                int(row["female_important"]),
                int(row["male_important"]),
            )

if __name__ == "__main__":
    # Replace with your uploaded file path
    csv_path = csv_path = r"C:\Users\aksha\Downloads\food_dataset_8500.csv"
    import_from_csv(csv_path)
