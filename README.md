# Android Hint Spinner

## Sample App

![](art/AndroidHintSpinner.gif)

A hint spinner that allows showing a hint, similar to the HTML Select &lt;option value="">Select your option&lt;/option>

You can use either a default or a custom layout.

## Including the library
Add this dependency to your build.gradle file:
```java
dependencies {
    compile 'me.srodrigo:androidhintspinner:1.0.0'
}
```

## Usage
### Hint functionality
The HintSpinner class works as a container, binding a plain Android Spinner and a HintAdapter, which manages the hint. A callback is needed at the moment to skip the hint selected event, as otherwise it would have to be handled by the user (this might be optional in future versions).

All you need to do is to instantiate HintSpinner and call the init() method. This will automatically select the hint for you and bind the spinner to the adapter. You can select the hint again manually when you want, just calling the selectHint() method.

### Default layout
The HintAdapter class uses the default spinner layout by default and no extra configuration is required. You just need to instanciate the HintSpinner with the default layout constructor:

```java
HintSpinner<String> hintSpinner = new HintSpinner<>(
				yourAndroidSpinner,
				// Default layout - You don't need to pass in any layout id, just your hint text and
				// your list data
				new HintAdapter<>(this, R.string.your_hint_text, yourStringsList),
				new HintSpinner.Callback<String>() {
					@Override
					public void onItemSelected(int position, String itemAtPosition) {
						// Here you handle the on item selected event (this skips the hint selected event)
					}
				});
hintSpinner.init();
```

### Custom layout
You can use whatever layout you want by overriding the HintAdapter#getCustomView() method. You can use any entity to store the data you want to show on your layout.
```java
HintAdapter<User> hintAdapter = new HintAdapter<User>(
						this,
						R.layout.your_custom_layout,
						R.string.your_hint_text,
						usersList) {

					@Override
					protected View getCustomView(int position, View convertView, ViewGroup parent) {
						final User user = getItem(position);
						final String name = user.getName();
						final String lastName = user.getLastName();

						// Here you inflate the layout and set the value of your widgets
						View view = inflateLayout(parent, false);
						view.findViewById(R.id.user_image_view).setBackgroundResource(
								R.drawable.ic_action_face_unlock);
						((TextView) view.findViewById(R.id.user_name_text_view)).setText(name);
						((TextView) view.findViewById(R.id.user_last_name_text_view)).setText(lastName);

						return view;
					}
				}

HintSpinner<User> hintSpinner = new HintSpinner<>(
				yourAndroidSpinner,
				hintAdapter,
				new HintSpinner.Callback<String>() {
					@Override
					public void onItemSelected(int position, String itemAtPosition) {
						// Here you handle the on item selected event (this skips the hint selected event)
					}
				});
hintSpinner.init();
```

## ChangeLog
** 1.0 **
Initial version.

## Developed by
Sergio Rodrigo Royo

Email: <a href="mailto:sergio.rodrigo.royo@gmail.com">sergio.rodrigo.royo@gmail.com</a>

Twitter: <a href="https://twitter.com/srodrigoDev">@srodrigoDev</a>

## License
The MIT License (MIT)

Copyright (c) 2015 Sergio Rodrigo

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
