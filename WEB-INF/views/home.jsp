<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Menggunakan data 'title' yang dikirim dari Controller -->
    <title>${title}</title>
    
    <style>
        body { font-family: sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f0f2f5; margin: 0; }
        .container { text-align: center; padding: 40px; background-color: white; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        h1 { color: #333; }
        p { color: #555; font-size: 1.1em; }
    </style>
</head>
<body>
    <div class="container">
        <!-- Menampilkan data 'title' di dalam H1 -->
        <h1>${title}</h1>
        <h1>kuontol semuanya</h1>
        <!-- Menampilkan data 'message' di dalam paragraf -->
        <p>${message}</p>
    </div>
</body>
</html>
