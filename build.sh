#!/bin/bash

# =====================================================
#   WARNA (FOREGROUND + BACKGROUND BADGE)
# =====================================================
RESET="\e[0m"
BOLD="\e[1m"

BG_ERROR="\e[41m\e[97m"
BG_WARN="\e[43m\e[30m"
BG_SUCCESS="\e[42m\e[30m"
BG_INFO="\e[44m\e[97m"

badge_error() { echo -e "${BG_ERROR}  [ERROR] $1  ${RESET}"; }
badge_warn() { echo -e "${BG_WARN}  [WARNING] $1  ${RESET}"; }
badge_success() { echo -e "${BG_SUCCESS}  [OK] $1  ${RESET}"; }
badge_info() { echo -e "${BG_INFO}  [INFO] $1  ${RESET}"; }

line() { echo -e "\e[36m──────────────────────────────────────────────────────${RESET}"; }

banner() {
    echo -e "\e[35m${BOLD}"
    echo "──────────────────────────────────────────────────────"
    echo "              F R A M E W O R K - B E T A"
    echo "                J A V A - C L I / W E B"
    echo "                  By TryCatch Wariors"
    echo "──────────────────────────────────────────────────────"
    echo -e "${RESET}"
}

# =====================================================
#   MULAI SCRIPT
# =====================================================
clear
banner

echo -e "\e[33m${BOLD} 1.${RESET} WEB   – Compile + Restart Tomcat"
echo -e "\e[33m${BOLD} 2.${RESET} CLI   – Compile & Run Main.java"
line
echo ""

read -p "Pilih mode (1/2): " choice
echo -e "${RESET}"

if [[ "$choice" != "1" && "$choice" != "2" ]]; then
    badge_error "Input tidak valid!"
    badge_warn "Gunakan angka 1 atau 2 saja."
    exit 1
fi

# =====================================================
#   PROJECT PATH
# =====================================================
APP_DIR="app"
OUTPUT_DIR="WEB-INF/classes"
LIB_DIR="WEB-INF/lib"

# -----------------------------------------------------
# AUTO-DETECT TOMCAT LOCATION
# -----------------------------------------------------

# ROOT SCRIPT: /SIA/webapps/ROOT/build.sh
TOMCAT_INSTANCE_ROOT="$(dirname $(dirname "$PWD"))"

# Pastikan folder bin ada
if [[ ! -d "$TOMCAT_INSTANCE_ROOT/bin" ]]; then
    badge_error "Folder bin Tomcat tidak ditemukan!"
    badge_warn "Seharusnya ada di: $TOMCAT_INSTANCE_ROOT/bin"
    exit 1
fi

TOMCAT_HOME="$TOMCAT_INSTANCE_ROOT"
TOMCAT_STOP="$TOMCAT_HOME/bin/shutdown.sh"
CATALINA_RUN="$TOMCAT_HOME/bin/catalina.sh"

badge_info "Tomcat terdeteksi di: $TOMCAT_HOME"

# =====================================================
#   PERSIAPAN
# =====================================================
badge_info "Membuat folder output..."
mkdir -p "$OUTPUT_DIR"

badge_info "Membersihkan file .class lama..."
find "$OUTPUT_DIR" -name "*.class" -delete 2>/dev/null

badge_info "Mengumpulkan library (.jar)..."
CLASSPATH=""
LIB_LIST=($(ls "$LIB_DIR"/*.jar 2>/dev/null))

for f in "${LIB_LIST[@]}"; do
    CLASSPATH="$CLASSPATH:$f"
done

CLASSPATH="$CLASSPATH:$OUTPUT_DIR"

badge_success "Classpath siap!"
echo

badge_info "Library yang ditemukan:"
if [ ${#LIB_LIST[@]} -eq 0 ]; then
    badge_warn "Tidak ada file .jar di folder lib."
else
    echo -e "\e[35m"
    echo "┌───────┬──────────────────────────────────────────────────────────────┐"
    echo "│  No   │ Nama File JAR                                                │"
    echo "├───────┼──────────────────────────────────────────────────────────────┤"
    index=1
    for jarfile in "${LIB_LIST[@]}"; do
        printf "│ %5d │ %-60s │\n" "$index" "$(basename "$jarfile")"
        index=$((index+1))
    done
    echo "└───────┴──────────────────────────────────────────────────────────────┘"
    echo -e "${RESET}"
fi

# =====================================================
#   MENGUMPULKAN FILE JAVA
# =====================================================
badge_info "Mengumpulkan file .java..."
rm -f sources.txt
find "$APP_DIR" -name "*.java" >> sources.txt 2>/dev/null

[[ -f "Env.java" ]] && echo "Env.java" >> sources.txt && badge_info "Env.java ditambahkan."
[[ -f "Main.java" ]] && echo "Main.java" >> sources.txt

# =====================================================
#   MODE WEB
# =====================================================
if [ "$choice" == "1" ]; then
    badge_info "MODE WEB DIPILIH"
    badge_info "Compiling..."

    javac -cp "$CLASSPATH" -d "$OUTPUT_DIR" @sources.txt

    if [ $? -ne 0 ]; then
        badge_error "Kompilasi gagal!"
        exit 1
    fi

    badge_success "Kompilasi berhasil!"
    line
    badge_info "Restarting Tomcat..."
    line

    if [[ -x "$TOMCAT_STOP" ]]; then
        "$TOMCAT_STOP"
    else
        badge_warn "shutdown.sh tidak bisa dieksekusi, mencoba chmod +x..."
        chmod +x "$TOMCAT_STOP"
        "$TOMCAT_STOP"
    fi

    sleep 3

    CATALINA_BASE="$TOMCAT_INSTANCE_ROOT" "$CATALINA_RUN" run

    line
    badge_success "BUILD + RESTART TOMCAT SELESAI!"
    line
fi

# =====================================================
#   MODE CLI
# =====================================================
if [ "$choice" == "2" ]; then
    badge_info "MODE CLI DIPILIH"
    badge_info "Compiling Main.java..."

    javac -cp "$CLASSPATH" -d "$OUTPUT_DIR" Main.java

    if [ $? -ne 0 ]; then
        badge_error "Kompilasi Main.java gagal!"
        exit 1
    fi

    badge_success "Kompilasi selesai!"
    clear
    badge_info "Menjalankan program..."

    java -cp "$CLASSPATH" Main

    badge_success "CLI SELESAI!"
fi
